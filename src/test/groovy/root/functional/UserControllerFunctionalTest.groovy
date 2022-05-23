package root.functional

import root.model.User

import static io.restassured.RestAssured.given
import static org.apache.http.HttpStatus.SC_BAD_REQUEST
import static org.apache.http.HttpStatus.SC_NOT_FOUND
import static org.apache.http.HttpStatus.SC_OK

class UserControllerFunctionalTest extends BaseFunctionalTest {

    private static final USER_ID = 'USER-ID'
    private static final USER_NAME_1 = 'USER-NAME-1'
    private static final USER_NAME_2 = 'USER-NAME-2'
    private static final USERS_URL = '/api/v1/users'
    private static final USER_URI = "$USERS_URL/$USER_ID"
    private static final JSON_CONTENT_TYPE = 'application/json'
    private static final REQUEST_BODY = '{"name":"$NAME"}'
    private static final INVALID_REQUEST_BODY = '{}'

    def 'should create user'() {
        given:
        assert userRepository.findAll().isEmpty()

        and:
        def requestBody = REQUEST_BODY.replace('$NAME', USER_NAME_1)

        when:
        def userId = given().contentType(JSON_CONTENT_TYPE)
                .body(requestBody)
                .when()
                .post(USERS_URL)
                .then()
                .statusCode(SC_OK)
                .extract().body().asString()

        then:
        userId.length() == 8

        and:
        userRepository.findById(userId).get().name == USER_NAME_1
    }

    def 'should not create user if request body is invalid'() {
        when:
        def response = given().contentType(JSON_CONTENT_TYPE)
                .body(INVALID_REQUEST_BODY)
                .when()
                .post(USERS_URL)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .extract().body().asString()

        then:
        response.contains("Field error in object 'createUserDto' on field 'name': rejected value [null]")
    }

    def 'should update user successfully'() {
        given:
        userRepository.saveAndFlush(
                User.builder().id(USER_ID).name(USER_NAME_1).build())

        and:
        def requestBody = REQUEST_BODY.replace('$NAME', USER_NAME_2)

        when:
        given().contentType(JSON_CONTENT_TYPE)
                .body(requestBody)
                .when()
                .put(USER_URI)
                .then()
                .statusCode(SC_OK)

        then:
        userRepository.findById(USER_ID).get() == User.builder().id(USER_ID).name(USER_NAME_2).build()
    }

    def 'should not update user if request body is invalid'() {
        when:
        def response = given().contentType(JSON_CONTENT_TYPE)
                .body(INVALID_REQUEST_BODY)
                .when()
                .put(USER_URI)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .extract().body().asString()

        then:
        response.contains("Field error in object 'updateUserDto' on field 'name': rejected value [null]")
    }

    def 'should not update user if it is not found'() {
        given:
        def requestBody = REQUEST_BODY.replace('$NAME', USER_NAME_1)

        when:
        def response = given().contentType(JSON_CONTENT_TYPE)
                .body(requestBody)
                .when()
                .put(USER_URI)
                .then()
                .statusCode(SC_NOT_FOUND)
                .extract().body().asString()

        then:
        response.contains("Resource is not found: User with id=$USER_ID is not found.")
    }

    def 'should get user by id successfully'() {
        given:
        def user = User.builder().id(USER_ID).name(USER_NAME_1).build()
        userRepository.saveAndFlush(user)

        when:
        def retrievedUser = given().when()
                .get(USER_URI)
                .then()
                .statusCode(SC_OK)
                .extract()
                .body()
                .as(User)

        then:
        retrievedUser == user
    }

    def 'should get 404 response code if user is not found by id'() {
        when:
        def response = given().when()
                .get(USER_URI)
                .then()
                .statusCode(SC_NOT_FOUND)
                .extract().body().asString()

        then:
        response.contains("Resource is not found: User with id=$USER_ID is not found.")
    }

    def 'should get users'() {
        given:
        def users = [
                User.builder().id('id-1').name('name-1').build(),
                User.builder().id('id-2').name('name-2').build()
        ]
        userRepository.saveAllAndFlush(users)

        when:
        def retrievedUsers = given().when()
                .get(USERS_URL)
                .then()
                .statusCode(SC_OK)
                .extract()
                .body()
                .as(User[])

        then:
        retrievedUsers == users
    }

    def 'should delete user'() {
        given:
        userRepository.saveAndFlush(User.builder().id(USER_ID).name(USER_NAME_1).build())

        and:
        assert userRepository.findAll().size() == 1

        when:
        given().when()
                .delete(USER_URI)
                .then()
                .statusCode(SC_OK)

        then:
        userRepository.findAll().isEmpty()
    }
}
