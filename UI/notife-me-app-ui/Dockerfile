FROM node:19-alpine
WORKDIR /appUI/
COPY package*.json /appUI/.

RUN npm install
COPY . /appUI/.

EXPOSE 5173
CMD ["npm", "run", "dev"]
