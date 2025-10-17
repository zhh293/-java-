# HMall - 鸿鹄商城

鸿鹄商城（HMall）是一个基于微服务架构的电商平台，包含商品管理、购物车、订单处理、支付等多个模块。
补充：
此项目仅仅是针对初学微服务的人，帮助其理解各种组件的应用方法，应用场景和架构思想，实现对分布式项目的快速上手，说是本身存在很多的缺陷，完全不适合商用，谢谢

## 项目结构
hmall
├── cart-service # 购物车服务
├── hm-api # API接口定义模块
├── hm-common # 公共模块
├── hm-geteway # 网关服务
├── hm-pay # 支付服务
├── hm-service # 用户服务
├── hm-trade # 交易服务
├── item-service # 商品服务
└── hm-user # 用户服务

## 技术栈

- **核心框架**: Spring Boot 2.7.12, Spring Cloud 2021.0.3
- **服务治理**: Nacos (服务注册与发现、配置管理)
- **数据库**: MySQL 8.0.24
- **持久层框架**: MyBatis-Plus 3.5.2
- **限流熔断**: Sentinel
- **分布式事务**: Seata
- **负载均衡**: Spring Cloud LoadBalancer
- **API文档**: Knife4j (OpenAPI 2)
- **其他**: Feign, OkHttp, Redis, RabbitMQ

## 核心模块介绍

### hm-common 公共模块
包含项目公共的工具类、异常处理、返回结果封装等。

主要组件:
- `R<T>`: 统一返回结果类
- 异常处理机制
- 工具类集合

### item-service 商品服务
负责商品信息管理，包含商品的增删改查等功能。

实体类:
- [Item](file://E:\hmall2\hm-service\src\main\java\com\hmall\domain\po\Item.java#L21-L112): 商品持久化对象
- [ItemDTO](file://E:\hmall2\hm-api\src\main\java\com\hmall\api\dto\ItemDTO.java#L6-L33): 商品数据传输对象

### cart-service 购物车服务
管理用户购物车相关功能。

### hm-user 用户服务
处理用户注册、登录、认证等用户相关功能。

### hm-trade 交易服务
处理订单创建、管理等交易相关功能。

### hm-pay 支付服务
处理支付相关逻辑。

### hm-api 接口定义模块
定义各服务间的Feign接口，用于服务间调用。

### hm-gateway 网关服务
API网关，负责请求路由、鉴权、限流等功能。

## 主要特性

1. **微服务架构**：各功能模块拆分为独立服务
2. **服务注册与发现**：基于Nacos实现
3. **配置中心**：使用Nacos进行统一配置管理
4. **负载均衡**：集成Spring Cloud LoadBalancer
5. **服务调用**：使用OpenFeign进行服务间通信
6. **熔断降级**：集成Sentinel实现限流和熔断
7. **分布式事务**：使用Seata处理分布式事务
8. **统一异常处理**：全局异常捕获和处理机制
9. **API文档**：集成Knife4j提供接口文档
10. **安全机制**：JWT Token认证

## 环境要求

- JDK 11 或 JDK 17 (根据不同模块)
- MySQL 8.0+
- Nacos 服务
- Redis
- RabbitMQ (可选)
- Seata (可选，用于分布式事务)

## 部署说明

1. 启动Nacos服务
2. 启动Redis服务
3. 创建MySQL数据库并执行初始化脚本
4. 按依赖顺序启动各微服务:
   - hm-user/user-service
   - item-service
   - cart-service
   - hm-trade/trade-service
   - hm-pay/pay-service
   - hm-gateway
5. 启动前端项目(如果有的话)

## 开发规范

- 使用Lombok简化代码
- 使用Swagger注解描述API
- 统一返回结果格式R<T>
- 使用DTO进行服务间数据传输
- 使用PO进行持久化操作

## 注意事项

1. 项目中不同模块使用了不同的JDK版本(11和17)，请确保编译环境正确
2. 需要配置Nacos地址和相关配置
3. 数据库连接信息需要根据实际情况配置
4. 分布式事务需要配置Seata服务
