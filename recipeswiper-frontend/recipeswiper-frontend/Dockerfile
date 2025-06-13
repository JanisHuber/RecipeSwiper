# Build stage
FROM node:18 AS builder

# Set working directory
WORKDIR /app

# Copy and install dependencies
COPY package*.json ./
RUN npm install

# Copy the full project
COPY . .

# Build Angular app in production mode
RUN npm run build --prod

# Serve stage
FROM nginx:alpine

# Remove default nginx static files
RUN rm -rf /usr/share/nginx/html/*

# Copy built app to Nginx's web root
COPY --from=builder /app/dist/recipeswiper-frontend /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
