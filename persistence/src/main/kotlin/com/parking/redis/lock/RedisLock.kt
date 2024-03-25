package com.parking.redis.lock

import com.parking.redis.service.RedisService
import mu.KLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.Ordered
import org.springframework.core.StandardReflectionParameterNameDiscoverer
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RedisLock(
    val key: String,
    val name: String = "redisLock"
)

//해당 어노테이션의 우선순위
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@Component
class RedisLockAspect(
    private val redisService: RedisService<String>
) {

    @Pointcut("@annotation(RedisLock)")
    fun lockAnnotation() {

    }

    @Around("lockAnnotation() && @annotation(redisLock)")
    fun around(joinPoint: ProceedingJoinPoint, redisLock: RedisLock): Any? {
        val parameters = getParameterMapByName(joinPoint)

        require(parameters[redisLock.key] != null) {
            "parameter 와 일치하는 key 가 존재하지 않습니다 key : $redisLock.key"
        }

        val key = PREFIX_KEY + redisLock.key + parameters[redisLock.key]?.toString()
        val lock = redisService.getRLock(key)

        return try {
            val isAvailable = lock.tryLock(WAITING_TIME, RELEASE_TIME, TimeUnit.MILLISECONDS)

            if (!isAvailable) {
                logger.info("$key lock time out")
                return false
            }

            joinPoint.proceed()
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        } finally {
            lock.unlock()
        }

    }

    /**
     * 파라미터와 값을 매핑해주는 메서드
     * @param : annotation 이 선언된 메서드의 진입점에서 해당 메서드에 대한 정보를 담고 있는 객체
     * @return : parameter 와 실제 값을 매핑한 정보
     * */
    private fun getParameterMapByName(joinPoint: JoinPoint): Map<String, Any> {
        val parameterMapByName: MutableMap<String, Any> = mutableMapOf()
        // 파라미터의 값
        val args = joinPoint.args
        val signature = joinPoint.signature as MethodSignature
        // 메서드 정보
        val method = joinPoint.target.javaClass.getDeclaredMethod(
            signature.method.name,
            *signature.method.parameterTypes
        )
        // 파라미터의 이름
        val parameterNames = StandardReflectionParameterNameDiscoverer().getParameterNames(method) ?: return mutableMapOf()
        for (index in parameterNames.indices) {
            parameterMapByName[parameterNames[index]] = args[index]
        }
        return parameterMapByName
    }

    companion object : KLogging() {
        const val WAITING_TIME = 30_000L
        const val RELEASE_TIME = 100_000L
        const val PREFIX_KEY = "PARKING_LOCK_KEY_"
    }
}
