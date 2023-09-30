FROM node:19-alpine
WORKDIR /appUI
COPY package*.json /appUI/
COPY public/ /appUI/public
COPY src/ /appUI/src
RUN npm install

EXPOSE 5173
CMD ["npm", "run", "dev"]
