FROM node:16.15.0 as build-stage
WORKDIR /var/jenkins_home/workspace/gganbu/ec2-front
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
FROM nginx:stable-alpine as production-stage

RUN mkdir /app
WORKDIR /app
RUN mkdir ./dist

COPY --from=build-stage /var/jenkins_home/workspace/gganbu/ec2-front/dist ./dist
COPY ./nginx.conf /etc/nginx/conf.d

EXPOSE 3000
CMD ["nginx", "-g","daemon off;"]