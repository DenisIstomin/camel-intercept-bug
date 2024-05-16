package bug;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Tests extends CamelTestSupport {

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Override
    public boolean isUseRouteBuilder() {
        return false;
    }

    @Test
    void bug() throws Exception {
        context.addRoutes(new MyRouteBuilder());

        AdviceWith.adviceWith(context, "main-route-id", routeBuilder -> {
            routeBuilder.intercept().log("intercepted");
        });

        context.start();

        ProducerTemplate producerTemplate = context.createProducerTemplate();
        String body = producerTemplate.requestBody("direct:start", "hello", String.class);
        Assertions.assertEquals("expected", body);
    }
};


