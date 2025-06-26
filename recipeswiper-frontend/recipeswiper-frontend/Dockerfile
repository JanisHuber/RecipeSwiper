# Build stage
FROM node:18-alpine AS builder

WORKDIR /app

# Install required packages for node-gyp and compilation
RUN apk add --no-cache python3 make g++

# Copy all necessary files for build
COPY package*.json ./
COPY tsconfig*.json ./
COPY angular.json ./
COPY tailwind.config.js ./

# Clean npm cache and install dependencies
RUN npm cache clean --force

# Install dependencies with multiple fallback strategies
RUN npm ci --no-audit --no-fund --prefer-offline || \
    npm install --no-audit --no-fund --no-optional || \
    (rm -rf package-lock.json node_modules && npm install --no-audit --no-fund)

# Copy source code after dependencies are installed
COPY . .

# Remove any existing build artifacts
RUN rm -rf dist/ .angular/

# Build with increased memory and timeout
ENV NODE_OPTIONS="--max-old-space-size=8192"
ENV NG_CLI_ANALYTICS=false
RUN timeout 600 npm run build -- --configuration production || \
    (echo "Build failed, retrying..." && npm run build -- --configuration production)

# Serve stage
FROM nginx:alpine

RUN rm -rf /usr/share/nginx/html/*

COPY --from=builder /app/dist/recipeswiper-frontend/browser /usr/share/nginx/html

COPY default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
