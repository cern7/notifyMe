FROM node:19-alpine
COPY notife-me-app-ui/package*.json /app
WORKDIR /app 
RUN npm install
COPY notife-me-app-ui/* /app

EXPOSE 5173
CMD ["npm", "run", "dev"]
