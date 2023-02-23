## 技术栈

- Vue
- ES6
- Webpack
- Vue-cli
- Vue-router
- Vuex
- Axios
- Element-UI
- ECharts

## 功能点介绍:

- **服务注册&发现与调用：**

基于 Nacos 来实现的服务注册与发现，使用使用 Feign 来实现服务互调, 可以做到使用 HTTP 请求远程调用时能与调用本地方法一样的编码体验，开发者完全感知不到这是远程方法，更感知不到这是个 HTTP 请求。

- **服务鉴权:**

通过 JWT 的方式来加强服务之间调度的权限验证，保证内部服务的安全性。

- **负载均衡：**

将服务保留的 rest 进行代理和网关控制，除了平常经常使用的 node.js、nginx 外，Spring Cloud 系列的 zuul 和 rebbion，可以帮我们进行正常的网关管控和负载均衡。其中扩展和借鉴国外项目的扩展基于 JWT 的 Zuul 限流插件，方面进行限流。

- **熔断机制：**

因为采取了服务的分布，为了避免服务之间的调用“雪崩”，采用了 Hystrix 的作为熔断器，避免了服务之间的“雪崩”。

- **监控：**

利用 Spring Boot Admin 来监控各个独立 Service 的运行状态；利用 turbine 来实时查看接口的运行状态和调用频率；通过 Zipkin 来查看各个服务之间的调用链等。

- **数据权限**

利用基于 Mybatis 的 DataScopeInterceptor 拦截器实现了简单的数据权限

- **SaaS 的无感解决方案**

使用 Mybatis 拦截器实现对所有 SQL 的拦截，修改默认的 Schema，从而实现多租户数据隔离的目的。

- **二级缓存**

采用 J2Cache 操作缓存，第一级缓存使用内存(Caffeine)，第二级缓存使用 Redis。 由于大量的缓存读取会导致 L2 的网络成为整个系统的瓶颈，因此 L1 的目标是降低对 L2 的读取次数。
该缓存框架主要用于集群环境中。单机也可使用，用于避免应用重启导致的缓存冷启动后对后端业务的冲击。

- **优雅的 Bean 转换**

采用 Dozer 组件来对 DTO、DO、PO 等对象的优化转换

- **前后端统一表单验证**

严谨的表单验证通常需要 前端+后端同时验证， 但传统的项目，均只能前后端各做一次检验， 后期规则变更，又得前后端同时修改。
故在`hibernate-validator`的基础上封装了`pd-tools-validator-starter`起步依赖，提供一个通用接口，可以获取需要校验表单的规则，然后前端使用后端返回的规则，
以后若规则改变，只需要后端修改即可。

- **防跨站脚本攻击（XSS）**
- **当前用户信息注入器**
- **在线 API**

由于原生 swagger-ui 某些功能支持不够友好，故采用了国内开源的`swagger-bootstrap-ui`，并制作了 stater，方便 springboot 用户使用。

- **代码生成器**

基于 Mybatis-plus-generator 自定义了一套代码生成器， 通过配置数据库字段的注释，自动生成枚举类、数据字典注解、SaveDTO、UpdateDTO、表单验证规则注解、Swagger 注解等。

- **定时任务调度器**：

基于 xxl-jobs 进行了功能增强。（如：指定时间发送任务、执行器和调度器合并项目、多数据源）

- **大文件/断点/分片续传**

前端采用 webupload.js、后端采用 NIO 实现了大文件断点分片续传，启动 Eureka、Zuul、File 服务后，直接打开 docs/chunkUploadDemo/demo.html 即可进行测试。
经测试，本地限制堆栈最大内存 128M 启动 File 服务,5 分钟内能成功上传 4.6G+的大文件，正式服耗时则会受到用户带宽和服务器带宽的影响，时间比较长。

- **分布式事务**  
  集成了阿里的分布式事务中间件：seata，以 **高效** 并且对业务 **0 侵入** 的方式，解决 微服务 场景下面临的分布式事务问题。

## 版本参考

ps: 理论上： node 大于 8.10.x 就行，但最好当前比较新的稳定版 10.x.x 11.x.x 12.x.x

```
node -v
v10.16.3

npm -v
6.9.0

cnpm -v
cnpm@6.1.0 (/usr/local/lib/node_modules/cnpm/lib/parse_argv.js)
npm@6.11.3 (/usr/local/lib/node_modules/cnpm/node_modules/npm/lib/npm.js)
node@10.16.3 (/usr/local/bin/node)
npminstall@3.23.0 (/usr/local/lib/node_modules/cnpm/node_modules/npminstall/lib/index.js)
prefix=/usr/local
darwin x64 17.5.0
registry=https://r.npm.taobao.org

```

## 安装

```
npm install --registry=https://registry.npm.taobao.org

上面的命令报错？就逐个试试以下命令：

npm i node-sass --sass_binary_site=https://npm.taobao.org/mirrors/node-sass/
git config --global url."https://".insteadOf git://
npm install chromedriver --chromedriver_cdnurl=http://cdn.npm.taobao.org/dist/chromedriver

还不行？ 就用淘宝镜像吧...

# 安装cnpm
npm install -g cnpm --registry=https://registry.npm.taobao.org


cnpm install --registry=https://registry.npm.taobao.org
```

### 本地开发环境运行

```
npm run dev
```

### 访问：http://localhost:8080/

### 生产环境运行

```
npm run build:prod
```

### 运行测试

```
npm run test
```

### 修复文件中不规范的写法

```
npm run lint
```
