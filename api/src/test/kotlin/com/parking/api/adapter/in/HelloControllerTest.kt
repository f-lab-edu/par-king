package com.parking.api.adapter.`in`

import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.Assertions
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = ["local","persistence-local"])
class HelloControllerTest (
    private val mockMvc: MockMvc,
    @Value("\${hello.name}")
    val helloName: String
) : DescribeSpec({

    describe("HelloController Test") {

        it("HelloAPI return 값을 Return 해야한다.") {
            val result = mockMvc.perform(
                get("/hello"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk)
                .andReturn()

            val responseBody = result.response.contentAsString

            Assertions.assertEquals(helloName, responseBody)
        }
    }
})