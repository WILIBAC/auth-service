package co.com.pragma.api;

import co.com.pragma.api.dto.UserRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
public class RouterRest {
    /*@Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/usecase/otherpath"), handler::listenPOSTUseCase)
                .and(route(GET("/api/otherusercase/path"), handler::listenGETOtherUseCase))
                .and(route(POST("/api/v1/users"), handler::save));
    }*/
    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "save",
                    operation = @Operation(
                            operationId = "createUser",
                            summary = "Registrar usuario",
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = UserRequestDTO.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Usuario creado"),
                                    @ApiResponse(responseCode = "400", description = "Error de validación")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/usuarios/{id}",
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "getById",
                    operation = @Operation(
                            operationId = "getUserById",
                            summary = "Obtener usuario por id",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
                                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "list",
                    operation = @Operation(
                            operationId = "listUsers",
                            summary = "Listar usuarios (usar fields=ids para solo IDs)",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Listado de usuarios")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routes(Handler handler) {
        return org.springframework.web.reactive.function.server.RouterFunctions
                .route(POST("/api/v1/usuarios").and(accept(APPLICATION_JSON)), handler::save)
                .andRoute(GET("/api/v1/usuarios/{id}").and(accept(APPLICATION_JSON)), handler::getById)
                .andRoute(GET("/api/v1/usuarios").and(accept(APPLICATION_JSON)), handler::list);
    }
}
