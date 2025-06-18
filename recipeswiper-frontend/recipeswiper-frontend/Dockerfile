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

# Serve Stage
FROM nginx:alpine

# Default-HTML löschen
RUN rm -rf /usr/share/nginx/html/*

# App-Build kopieren
COPY --from=builder /app/dist/recipeswiper-frontend/browser /usr/share/nginx/html

# Eigene Nginx-Config kopieren und überschreiben
COPY default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
