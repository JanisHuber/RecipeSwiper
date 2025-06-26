# Build stage
FROM node:18-alpine AS builder

WORKDIR /app

# Install required packages for node-gyp and compilation
RUN apk add --no-cache python3 make g++

# Copy package files first for better caching
COPY package*.json ./
COPY tsconfig*.json ./
COPY angular.json ./
COPY tailwind.config.js ./

# Install dependencies - robust approach for CI/CD
RUN npm install --frozen-lockfile --no-audit --no-fund || \
    npm install --no-audit --no-fund

# Copy source code
COPY src/ ./src/
COPY public/ ./public/

# Build with proper memory limits for GitHub Actions
ENV NODE_OPTIONS="--max-old-space-size=4096"
RUN npm run build -- --configuration production

# Serve stage
FROM nginx:alpine

RUN rm -rf /usr/share/nginx/html/*

COPY --from=builder /app/dist/recipeswiper-frontend/browser /usr/share/nginx/html

COPY default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
