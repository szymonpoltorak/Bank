FROM node:18.15-alpine as build

WORKDIR /bank

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run ng build --configuration=docker

FROM nginx:alpine

COPY --from=build /bank/dist/bank-frontend /etc/nginx/html

COPY nginx.conf /etc/nginx/nginx.conf

COPY src/assets/ssl /etc/nginx/ssl

EXPOSE 80
EXPOSE 443
