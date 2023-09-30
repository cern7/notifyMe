FROM node:19-alpine
# Create a directory inside the container to copy the files
WORKDIR /app
# Copy the package.json and package-lock.json first to leverage Docker cache if dependencies haven't changed
COPY package*.json ./
# Install dependencies
RUN npm install
# Copy the rest of the app files
COPY . .

# Expose the required port
EXPOSE 5173
# Start the application
CMD ["npm", "run", "dev"]
