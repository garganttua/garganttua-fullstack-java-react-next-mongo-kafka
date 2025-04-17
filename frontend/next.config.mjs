import path from 'path';
import { fileURLToPath } from 'url';

// nécessaire pour __dirname en ESM
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  output: 'export',
  images: {
    unoptimized: true
  },
  webpack(config) {
    config.resolve.alias['@'] = path.resolve(__dirname, 'src/main/next');
    return config;
  }
};

export default nextConfig;