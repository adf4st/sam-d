# SAM-D Starter App : Web

Angular.js web front-end for the SAM-D stack starter application. 

## Getting Started

#### Starting web-app from scratch
1. `npm install`
1. `npm run build-start`

### Building web-app 
1. `npm run build-js`

## Dockerfile
The Dockerfile in the root web directory starts from a base node 6 image and copies in the `package.json`
file, runs an `npm install`, and that starts the application.  
[Docker Repo](https://hub.docker.com/r/afabian/samd-web/) 

## Browserify
Browserify is used to compile all js files into a single `bundle.js` file using require().   
Browserify-css is used to compile all css files into a single `app.css` file using @import statements.
Before starting your server, run the following command to compile the js and css. 

`browserify -g browserify-css app.js > bundle.js`

During development you can also use `watchify` to compile your js on the fly:

`watchify --entry app.js --outfile bundle.js`


## Json Web Tokens
JWTs are used for user authentication. The [Angular-JWT](https://github.com/auth0/angular-jwt) library is used to ease the integration with JWTs and Angular. 
This library makes it easy to limit routes to authenticated users. Just include 'requiresLogin: true' when defining your state.

```js
  $stateProvider.
      state("private", {
          url: "/private",
          templateUrl: "components/private/private.html",
          controller: "PrivateCtrl",
          data: {
              requiresLogin: true
          }
      })
```

## User Accounts
User login and new user registration are included with the SAM-D sample app. Users authenticate with a username and password and JSON Web Tokens are used for web client authorization to the API.

[Login Page](http://samd.alexfabian.net/#!/login)  

Sample User Accounts : 
- admin user
    - admin
    - admin
- non-admin user
    - user
    - user