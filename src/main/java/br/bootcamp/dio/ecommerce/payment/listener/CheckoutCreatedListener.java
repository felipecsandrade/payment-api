package br.bootcamp.dio.ecommerce.payment.listener;

import br.bootcamp.dio.ecommerce.checkout.event.CheckoutCreatedEvent;
import br.bootcamp.dio.ecommerce.payment.event.PaymentCreatedEvent;
import br.bootcamp.dio.ecommerce.payment.streaming.CheckoutProcessor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CheckoutCreatedListener {

    private final CheckoutProcessor checkoutProcessor;

    @StreamListener(CheckoutProcessor.INPUT)
    public void handler(CheckoutCreatedEvent event){//quem eu vou escutar

        // Processar pagamento gateway

        //Salvar os dados de pagamento no banco de dados

        //Enviar o evento do pagamento processado
        final PaymentCreatedEvent paymentCreatedEvent = PaymentCreatedEvent.newBuilder()
                .setCheckoutCode(event.getCheckoutCode())
                .setCheckoutStatus(event.getStatus())
                .setPaymentCode(UUID.randomUUID().toString())
                .build();
        checkoutProcessor.output().send(MessageBuilder.withPayload(paymentCreatedEvent).build());

    }
}
