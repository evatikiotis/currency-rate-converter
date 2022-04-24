package pl.cleankod.exchange.provider;

import pl.cleankod.exchange.core.domain.Money;
import pl.cleankod.exchange.core.gateway.CurrencyConversionService;
import pl.cleankod.exchange.provider.nbp.ExchangeRatesNbpClient;
import pl.cleankod.exchange.provider.nbp.model.RateWrapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class CurrencyConversionNbpServiceWithCircuitBraker implements CurrencyConversionService {
    private final ExchangeRatesNbpClient exchangeRatesNbpClient;

    public CurrencyConversionNbpServiceWithCircuitBraker(ExchangeRatesNbpClient exchangeRatesNbpClient) {
        this.exchangeRatesNbpClient = exchangeRatesNbpClient;
    }

    @Override
    public Money convert(Money money, Currency targetCurrency) {
        FetchRatesCommand fetchRatesCommand = new FetchRatesCommand(
                "nbpClient",
                exchangeRatesNbpClient,
                money,
                targetCurrency);
//        RateWrapper rateWrapper = exchangeRatesNbpClient.fetch("A", targetCurrency.getCurrencyCode());
//        BigDecimal midRate = rateWrapper.rates().get(0).mid();
//        BigDecimal calculatedRate = money.amount().divide(midRate, RoundingMode.HALF_UP);
//        return new Money(calculatedRate, targetCurrency);
        return fetchRatesCommand.execute();
    }
}
