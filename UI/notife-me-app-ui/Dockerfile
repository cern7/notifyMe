FROM node:19-alpine
WORKDIR /appUI
COPY package*.json /appUI/
COPY notife-me-app-ui/public /appUI/public
COPY notife-me-app-ui/src /appUI/src
RUN npm install

EXPOSE 5173
CMD ["npm", "run", "dev"]
