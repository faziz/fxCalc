# FxCalculator

Someone asked me to solve a programming challenge to gauge my skills, the problem was complex enough for me to use for scratch off my itch to get hands on Angular 2.
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
    87.11 USD to NZD = 87.11 * ( 1 / direct_rates(NZDUSD) ) = 87.11 * ( 1 / 0.775 ) = 115.377

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
Give examples
```

### Installing

A step by step series of examples that tell you have to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc

