package com.sivalabs.bookmarker.controller

import com.sivalabs.bookmarker.model.AuthenticationRequest
import com.sivalabs.bookmarker.model.AuthenticationResponse
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
abstract class AbstractIntegrationTest {

    @Autowired
    protected lateinit var restTemplate: TestRestTemplate

    protected fun getAuthHeaders(): HttpHeaders {
        val responseEntity = authenticate("admin@gmail.com", "admin")
        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer ${responseEntity.body?.accessToken}")
        return headers
    }

    protected fun authenticate(username: String, password: String): ResponseEntity<AuthenticationResponse> {
        val authenticationRequest = AuthenticationRequest(username, password)
        val request = HttpEntity(authenticationRequest)
        return restTemplate.postForEntity("/api/auth/login", request, AuthenticationResponse::class.java)
    }
}
