package com.mindhub.homebanking;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.mindhub.homebanking.utils.cardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class cardUtilsTest {

    @Test

    public void cardNumberIsCreated(){

        String cardNumber = cardUtils.getCardNumber();

        assertThat(cardNumber,is(not(emptyOrNullString())));

    }

    @Test

    public void cardNumberCvv(){
        String cardCvv= cardUtils.getCardNumber();

        assertThat(cardCvv,is(not(emptyOrNullString())));

    }



}
