# Build stage
FROM node:18 AS builder

WORKDIR /app

# Copy package files first for better caching
COPY package*.json ./
COPY tsconfig*.json ./
COPY angular.json ./
COPY tailwind.config.js ./

RUN npm ci --only=production=false

# Copy source code
COPY src/ ./src/
COPY public/ ./public/

# Nutze npm, damit ng über ./node_modules/.bin/ng ausgeführt wird
RUN npm run build -- --configuration production

# Serve stage
FROM nginx:alpine

RUN rm -rf /usr/share/nginx/html/*

COPY --from=builder /app/dist/recipeswiper-frontend/browser /usr/share/nginx/html

COPY default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
