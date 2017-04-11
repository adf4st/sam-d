
## Getting Started

1) `npm install`
1) `npm run build-start`

## Browserify
Browserify is used to compile all js files into a single `app.js` file using require().   
Browserify-css is used to compile all css files into a single `app.css` file using @import statements.
Before starting your server, run the following command to compile the js and css.   

`browserify -g browserify-css app_dev.js > app.js`

During development you can also use `watchify` to compile your js on the fly:

`watchify --entry app_dev.js --outfile app.js`


## Json Web Tokens
JWTs are used for user authentication. The following library is used to ease the use of JWTs with Angular.  

https://github.com/auth0/angular-jwt


### Sample Accounts

- admin user
    - admin
    - admin
- non-admin user
    - user
    - user
    
    
    
### Todos
- add content to home page that describes what app does
- general clean up
- Dockerize / run in node
- put on github
