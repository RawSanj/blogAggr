# blogAggr

Spring Boot - Angular - MongoDB complete full-stack webapp for aggregating RSS Feeds.

#### Features :
1. Add any RSS Feed link of any Blog once and get all the articles from that. The Old articles are stored in database, so you won't loose those.
2. Registration via email, Forgot Password, Account activation is available.
3. Social Login via Facebook, Google and Twitter using Spring-Social.
4. Responsive Bootstrap design with Bootswatch Themes.
5. Internationalization - English and French (comes out of box from jHipster). 
6. Health Checks, Metrics, Audit, Logs, User Management, ect thanks to jHispter and Spring Boot Actuator.

This application is developed with AngularJs and Spring Boot using JHipster, you can find documentation and help at [https://jhipster.github.io](https://jhipster.github.io).

## Demo

- On Heroku - https://blogaggr.herokuapp.com (Heroku Free account has limitations, application goes to sleep, so it may take a while to load).

- On Pivotal CloudFoundry - http://blogaggregator.cfapps.io (Pivotal CF is free for 2 months, this link won't work after that!).

## Installation

```sh
$ git clone https://github.com/RawSanj/blogAggr.git
```
Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js][]: We use Node to run a development web server and build the project.
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.

After installing Node, you should be able to run the following command to install development tools (like
[Bower][] and [BrowserSync][]). You will only need to run this command when dependencies change in package.json.

    npm install

We use [Grunt][] as our build system. Install the grunt command-line tool globally with:

    npm install -g grunt-cli

Run the following commands in two separate terminals to create a blissful development experience where your browser
auto-refreshes when files change on your hard drive.

    mvn
    grunt

Bower is used to manage CSS and JavaScript dependencies used in this application. You can upgrade dependencies by
specifying a newer version in `bower.json`. You can also run `bower update` and `bower install` to manage dependencies.
Add the `-h` flag on any command to see how you can use it. For example, `bower update -h`.

## Building for production

To optimize the blogaggr client for production, run:

    mvn -Pprod clean package

This will concatenate and minify CSS and JavaScript files. It will also modify `index.html` so it references
these new files.

To ensure everything worked, run:

    java -jar target/*.war --spring.profiles.active=prod

Then navigate to [http://localhost:8080](http://localhost:8080) in your browser.

## Testing

Unit tests are run by [Karma][] and written with [Jasmine][]. They're located in `src/test/javascript` and can be run with:

    grunt test

UI end-to-end tests are powered by [Protractor][], which is built on top of WebDriverJS. They're located in `src/test/javascript/e2e`
and can be run by starting Spring Boot in one terminal (`mvn spring-boot:run`) and running the tests (`grunt itest`) in a second one.

## Deploy to Cloud 

### Heroku
1. Create an Application on Heroku.
2. Install required Heroku dependencies - https://toolbelt.heroku.com
3. Since Heroku asks for Credit Card to use MongoDB Add-ons, you can create MongoDB instance at MongoLab and map it in your application to use that instead of using Mongo instance from Heroku. 
4. Follow this guide to deploy - https://devcenter.heroku.com/articles/git

### CloudFoundry
1. Create an Application on CloudFoundry.
2. Install required CF dependencies - http://docs.cloudfoundry.org/devguide/installcf
3. Run this command:
`cf push <AppNameCreatedInCF> -p target/blogaggr-1.0.0.war -b https://github.com/cloudfoundry/java-buildpack.git`

### Using JHipster generator
1. Follow this [CF Guide] to deploy in CloudFoundry.
2. Follow this [Heroku Guide] to deploy in Heroku.

__Note:__ I have spent a lot of time configuring application to deploy in Cloud, feel free to contact me if you have any issues trying above methods.


## Tools and Tech

The following tools are used to create this project :

* [Spring Boot] - (Srping Security, Spring Data MongoDB, Spring Social)
* [Angular.Js]
* [Node.js]
* [JHipster]
* [Spring Tool Suite]
* [Git]

## License
----

The MIT License (MIT)

Copyright (c) 2016. Sanjay Rawat

[JHipster]: https://jhipster.github.io/
[Node.js]: https://nodejs.org/
[Bower]: http://bower.io/
[Grunt]: http://gruntjs.com/
[BrowserSync]: http://www.browsersync.io/
[Karma]: http://karma-runner.github.io/
[Jasmine]: http://jasmine.github.io/2.0/introduction.html
[Protractor]: https://angular.github.io/protractor/
[Spring Boot]: http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/
[Angular.Js]: https://angularjs.org/
[Spring Tool Suite]: https://spring.io/tools
[Git]: https://git-scm.com/
[CF Guide]: https://jhipster.github.io/cloudfoundry/
[Heroku Guide]: https://jhipster.github.io/heroku/