import { build } from 'vite';
import vue from '@vitejs/plugin-vue';

await build({
  configFile: false,
  plugins: [vue()],
  build: {
    outDir: '.tmp-build',
    emptyOutDir: false
  },
  server: {
    port: 5173
  }
});
