-- 创建聊天记录表
CREATE TABLE IF NOT EXISTS `chat_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '聊天记录ID',
  `from_name` varchar(50) NOT NULL COMMENT '发送者用户名',
  `to_name` varchar(50) NOT NULL COMMENT '接收者用户名',
  `message` text NOT NULL COMMENT '消息内容',
  `send_time` datetime NOT NULL COMMENT '发送时间',
  `message_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '消息类型（0:文本消息, 1:图片消息, 2:表情消息）',
  `is_read` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已读（0:未读, 1:已读）',
  PRIMARY KEY (`id`),
  KEY `idx_from_name` (`from_name`),
  KEY `idx_to_name` (`to_name`),
  KEY `idx_send_time` (`send_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='聊天记录表'; 