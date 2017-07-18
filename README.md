# FxCalculator

Someone asked me to solve a programming challenge to gauge my skills, the problem was complex enough for me to use to scratch off my itch to get hands-on on Angular 2.
It's basically a simple currency to currency calculator. 

src/main/resources/rate_matrix.xlsx defines two tabs direct_rates and rate_matrix.

direct_rates tab define direct inter-currency exchange rate.
In cases where there is no direct rate available rate_matrix sheet define the related rate in terms of another currency.

When the user selects the currencies and amount and ask the program to calculate the rate the program first look at the rate_matrix to determine the inter currency rate relation in following order,

1. If the rate is determined to be '1:1' it means no conversion needs to take place, e.g. 100 AUD in AUD would be 100.
2. If the rate is determined to be 'D', then direct_rates tab is looked up to determine the rate, e.g. 100 AUD in USD would be determined to be 0.8371 (AUDUSD) this rate is then applied to 100 AUD to determine the converted amount.
3. If the rate is determined to be 'INV', it means that the direct rate is available in the form of inversion, e.g. NOK to EUR is not available in the direct rates but EUR to NOK is available so following calculation takes place,
    ConvertedAmount = amount * (1 / direct_rate(EURNOK))
4. If the rate is determined to be another currency, then the converted amount get calculated in two steps,
    e.g. CAD in NZD
        a. In rate matrix tab, CAD -> NZD is mapped to USD, so first we need to convert the amount from CAD to USD
        b. In the second step we would need to convert USD to NZD. In the rate matrix tab the USD to NZD is determined to be 'INV' so we will apply the rule number 3. If it had been 'D' we would have applied rule number 2.
    100 CAD in NZD would be calculated this ways,
    100 CAD to USD = 100 * direct_rates(CADUSD) = 100 * 0.8711 = 87.11
    87.11 USD to NZD = 87.11 * ( 1 / direct_rates(NZDUSD) )

## Getting Started

Just run the main class from maven FxCalculatorStarter to get the application loaded.

### Prerequisites

Server is based on Apache Maven which nicely resolves the dependencies and bring the Spring Boot based application by running the main class FxCalculatorStarter.
Web is managed by Angular4 so you need to have all the node and angular junk already running on your machine if you feel like making changes to the web side. 

To run the Angular application just run ng start, once satisfied withe changes run ng build so that Angular artifacts become part of Spring Boot based web application.
