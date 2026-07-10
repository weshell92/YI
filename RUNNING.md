# YI 本地运行与部署说明

本项目已改造为纯静态前端应用，可直接部署到 GitHub Pages，无需启动后端。

## 项目结构
- `yijing-web`：Vue 3 前端，包含首页、学习模块、八卦、六十四卦、案例库、在线起卦、AI 解卦、用户中心和后台管理页面。
- `public/data/`：静态数据目录，包含八卦、六十四卦、爻辞、案例、课程、提示词等 JSON 文件。
- `.github/workflows/deploy.yml`：GitHub Actions 自动部署工作流。

## 本地开发

要求：
- Node.js 18+
- npm

```powershell
cd yijing-web
npm install
npm run dev
```

默认地址：`http://localhost:5173`

## 本地构建与预览

```powershell
cd yijing-web
npm run build
npm run preview
```

预览地址：`http://localhost:4173/YI/`

## 部署到 GitHub Pages

1. 在 GitHub 仓库设置中启用 Pages：
   - Source 选择 **GitHub Actions**。
2. 确认 `yijing-web/vite.config.mjs` 中的 `base` 与仓库名一致：
   - 若仓库名为 `YI`，则 `base: '/YI/'`。
   - 若仓库名不同，请同步修改。
3. 推送代码到 `main` 分支，GitHub Actions 会自动构建并部署到 `gh-pages`。
4. 部署完成后，访问 `https://<username>.github.io/YI/`。

## 数据维护

- 站点内容来自 `yijing-web/public/data/*.json`，直接替换这些 JSON 文件即可更新内容。
- 起卦记录、复盘、后台管理演示数据均保存在浏览器 `localStorage` 中，换浏览器或清空缓存会丢失。

## 注意

- 当前 `yijing-web/public/data/hexagrams.json` 为示例数据，后续可替换为完整 64 卦数据。
- 后台管理页面为本地演示版，数据不会同步到服务器。
