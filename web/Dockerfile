#FROM node:6.9.0
FROM node:6-alpine
MAINTAINER Alex Fabian <afabian@captechconsulting.com>

RUN npm update

RUN npm cache clean

# install dependencies in their own folder. Having these as seperate commands will allow docker to cache these so they
# are only redownloaded if the package.json files change
ADD package.json /tmp/package.json
RUN cd /tmp && npm install --production  # remove the '--production' if you want dev-dependencies installed

# make the workspace directory and copy over the dependencies from the temporary directory into the workspace directory
RUN mkdir -p /workspace/app && cp -a /tmp/node_modules /workspace/app/ && rm -rf /tmp

WORKDIR /workspace/app
ADD . /workspace/app

EXPOSE 80

ENTRYPOINT ["npm", "start"]