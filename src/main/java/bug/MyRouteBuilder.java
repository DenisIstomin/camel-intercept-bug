package bug;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {
        onException(Exception.class)
                .log(LoggingLevel.ERROR, "handler", "called");

        from("direct:start").routeId("main-route-id")
          .doTry()
            .throwException(new Exception())
          .doCatch(Exception.class)
            .setBody(constant("expected"))
          .end();
    }

}
