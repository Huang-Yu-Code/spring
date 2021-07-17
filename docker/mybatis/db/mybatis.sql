DROP DATABASE IF EXISTS `mybatis`;
USE `mybatis`;
DROP TABLE IF EXISTS `entity`;
CREATE TABLE `entity`
(
    `id`          int                                                          NOT NULL AUTO_INCREMENT,
    `name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `gender`      tinyint                                                      NULL DEFAULT 1,
    `money`       decimal(10, 0) UNSIGNED                                      NULL DEFAULT 0,
    `create_time` datetime                                                     NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;