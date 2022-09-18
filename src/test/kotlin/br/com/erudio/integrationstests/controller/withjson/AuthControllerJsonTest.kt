package br.com.erudio.integrationstests.controller.withjson


import br.com.erudio.integrationstests.TestConfigs
import br.com.erudio.integrationstests.testcontainers.AbstractIntegrationTest
import br.com.erudio.integrationstests.vo.AccountCredentialsVO
import br.com.erudio.integrationstests.vo.TokenVO
import io.restassured.RestAssured.given
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerJsonTest : AbstractIntegrationTest() {

    private lateinit var tokenVO: TokenVO

    @BeforeAll
    fun setup() {
        tokenVO = TokenVO()
    }

    @Test
    @Order(1)
    fun testSignin() {
        val user = AccountCredentialsVO()
        user.username = "usuario1"
        user.password = "admin123"

        tokenVO = given()
            .basePath("/auth/signin")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(user)
            .`when`()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(TokenVO::class.java)

        assertNotNull(tokenVO.accessToken)
        assertNotNull(tokenVO.refreshToken)
    }

    @Test
    @Order(2)
    fun testRefresh() {
        val newTokenVO: Any = given()
            .basePath("/auth/refresh")
            .port(TestConfigs.SERVER_PORT)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .pathParam("username", tokenVO.username)
            .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.refreshToken)
            .`when`()
            .put("{username}")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(TokenVO::class.java)

        assertNotNull(tokenVO.accessToken)
        assertNotNull(tokenVO.refreshToken)
    }
}