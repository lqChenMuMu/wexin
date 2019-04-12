/*
 Navicat Premium Data Transfer

 Source Server         : LOCA
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : db_wx

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 12/04/2019 17:48:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for appointment
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment`  (
  `id` int(11) NOT NULL,
  `personnel_id` int(11) NULL DEFAULT NULL,
  `class_id` int(11) NULL DEFAULT NULL,
  `time` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `telphone` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `submitTime` varchar(0) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `appointmentType` int(11) NULL DEFAULT NULL COMMENT '1：正常预约 2：手动设置',
  `appointmentState` int(11) NULL DEFAULT NULL COMMENT '0：为处理 1：已处理',
  `is_delete` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for first_class
-- ----------------------------
DROP TABLE IF EXISTS `first_class`;
CREATE TABLE `first_class`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of first_class
-- ----------------------------
INSERT INTO `first_class` VALUES (1, '综合服务\r', NULL);
INSERT INTO `first_class` VALUES (2, '专项咨询\r', NULL);
INSERT INTO `first_class` VALUES (3, '项目服务\r', NULL);
INSERT INTO `first_class` VALUES (4, '其他服务', NULL);

-- ----------------------------
-- Table structure for material
-- ----------------------------
DROP TABLE IF EXISTS `material`;
CREATE TABLE `material`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `material_text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for second_class
-- ----------------------------
DROP TABLE IF EXISTS `second_class`;
CREATE TABLE `second_class`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `first_class_id` int(11) NULL DEFAULT NULL,
  `details` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of second_class
-- ----------------------------
INSERT INTO `second_class` VALUES (1, '政策咨询', 1, '政策咨询亦称综合咨询、决策咨询，指对带有全面性、战略性、综合性并且能在一定程度上影响政策的问题提供的咨询。其主要内容包括：社会、经济、科技等各方面长远规划的制订;地区性、区域性综合开发方案的设计;跨地区、跨部门、跨行业政策的研究;能源、交通、环境、资源等问题的前景预测等。', NULL);
INSERT INTO `second_class` VALUES (2, '路径设计', 1, '路径规划是运动规划的主要研究内容之一。运动规划由路径规划和轨迹规划组成，连接起点位置和终点位置的序列点或曲线称之为路径，构成路径的策略称之为路径规划', NULL);
INSERT INTO `second_class` VALUES (3, '需求对接', 1, '技术对接是指参与到为提高生产力水平而对科学研究与技术开发所产生的，具有实用价值的科技成果，进行后续试验、开发、应用、推广直至形成新产品、新工艺、新材料，发展新产业的整个过程。', NULL);
INSERT INTO `second_class` VALUES (4, '项目申报', 1, '项目申报是指政府机关针对企业或其他研究单位作出的一系列优惠政策，企业或相关研究单位再根据政府的政策进行编写申报文件然后根据相关申报要求和流程进行申报。 可申请的项目包括国家省市资助计划体系及各部委资助计划比如863计划，973计划，科技型中小企业创新基金，中小企业发展专项资金，企业科技新技术，国家社科基金，国家自科基金，省社科基金、省自科基金等不同级别的项目，申请时难度不同，申请人的资格和条件也各有不同。', NULL);
INSERT INTO `second_class` VALUES (5, '专项培训', 1, '专项培训就是指对于某一个技能给予的培训.一般都是企业为了留住人才而开出的条件,但是,这种情况下,企业都会要求员工签一份比较长的合同,同时还有不小的违约金的', NULL);
INSERT INTO `second_class` VALUES (6, '国防专利申请', 1, '《国防专利》主要内容包括：军用标准化、国防计量、军工质量管理、科技情报、成果管理、国防专利是国防科技技术基础的重要组成部分。1982年国防科工委成立后，在国务院、中央军委的领导下，将国防科技的技术基础工作统一管理起来。经过二十年来的不懈努力，借鉴国外经验，结合国情，制定了我国自己的军用标准，并得到有效的贯彻实施；建立起使用、科研、生产统一的计量传递体系，有效地保证了科研、试验、生产任务，为国防科技和武器装备的发展提供了有力的技术支撑', NULL);
INSERT INTO `second_class` VALUES (7, '军工相关资质申请\r', 2, 'GJB9001军工产品质量管理体系，是根据《军工产品质量管理条例》(简称《条例》)的要求，在ISO9001标准的基础上，增加军用产品的特殊要求编制的。军用系列标准的发布和实施，推动了军工产品质量管理体系建设的迅速发展，促进了军用产品质量与可靠性水平的提高。', NULL);
INSERT INTO `second_class` VALUES (8, '涉密场所保密条件建设咨询\r', 2, '《保密条例》是军队保密工作的基本依据。《保密条例》自1996年3月24日由中央军委发布施行以来，对于规范全军人员的保密行为，维护军事秘密安全，保障军事斗争准备和军队建设的顺利进行，发挥了重要作用。近年来，军队保密形势和客观环境发生了深刻变化，国家和军队保密工作的政策制度有了新的发展，亟需对现行条例进行修订完善', NULL);
INSERT INTO `second_class` VALUES (9, '项目申报咨询\r', 2, '可行性研究报告，简称可研报告，是在制订生产、基建、科研计划的前期，通过全面的调查研究，分析论证可行性研究报告流程某个建设或改造工程、某种科学研究、某项商务活动切实可行而提出的一种书面材料。', NULL);
INSERT INTO `second_class` VALUES (10, '企业管理诊断与咨询', 2, '企业管理咨询 [1]  是在企业管理咨询实践基础上形成的一门应用性学科，是适应企业生存和发展的客观需要，帮助企业解决在成长过程中的各种问题，提高其对环境的适应能力和参与市场竞争的能力，促进管理咨询业的发展而建立起来的。这一学科建立的时间不长，有很多理论和实践问题需要我们努力去探索', NULL);
INSERT INTO `second_class` VALUES (11, '技术评价与升级指导', 2, '《科学技术评价办法》是为规范科学技术评价工作，建立健全科学技术评价机制，正确引导科学技术工作健康发展，增强我国的科学技术持续创新能力，提高我国科学技术的实力和水平，根据科学技术部、教育部、中国科学院、中国工程院、国家自然科学基金委员会联合印发的《关于改进科学技术评价工作的决定》和国家有关法律法规制定了《科学技术评价办法》。', NULL);
INSERT INTO `second_class` VALUES (12, '招投标、造价标准化推进\r', 2, '集中招标指集团相关企业具有相同的物资、工程及咨询服务类招标需求，为了发挥规模优势，降低采购成本，由专门政府认可的招标单位负责组织统一进行招标的方式。定标后由各企业直接与中标人签订采购、承包或服务合同，并以网站等方式公布，具有合法规程多企业竞争的招标方式。实际的履约人为各企业。\r\n', NULL);
INSERT INTO `second_class` VALUES (13, '技术和项目孵化与落地\r', 3, '侨梦苑，全称为侨梦苑侨商产业聚集区，位于天津市武清区，主要是为吸引华侨华人企业投资而设立的一个产业区，集文化休闲、经济技术开发与商务区为一体。', NULL);
INSERT INTO `second_class` VALUES (14, '融资与股权结构设计咨询', 3, '股权设计就是公司组织的顶层设计。传统企业互联网转型，战略和商业模式解决做什么、怎么做，股权设计解决的是谁投资、谁来做、谁收益的问题。', NULL);
INSERT INTO `second_class` VALUES (15, '知识产权转移\r转让', 3, '知识产权转让，是指知识产权出让主体与知识产权受让主体，根据与知识产权转让有关的法律法规和双方签定的转让合同，将知识产权权利享有者由出让方转移给受让方的法律行为。在无特别说明的情况下，本文所说的知识产权转让仅指合同转让，不包括因继承、继受等方式的转让', NULL);
INSERT INTO `second_class` VALUES (16, '孵化中心', 4, '孵化中心设立科技创新项目孵化区、文化创意项目孵化区、科学养生项目孵化区等，并由基地运营企业联合新安街道创业帮扶协会成立基地综合管理委员会，依托企 业投资者联合会资源，以示范企业、龙头企业带动初创企业，最后形成研发设计、创意增值、产品展销、产品发布、工业设计、品牌策划、艺术品交易、软件开发、 电子商务、创业培训等为一体的创意孵化中心。', NULL);
INSERT INTO `second_class` VALUES (17, '信息平台', 4, '自20世纪70年代以来，人类的实践活动框架开始由工业平台进入到信息平台（information platform），社会技术形态开始转型。信息平台正在改变我们时代人与世界的中介（medium）方式，改变人类的作战方式和战争形态，它是军事实践新的元起点', NULL);
INSERT INTO `second_class` VALUES (18, '金融服务', 4, '金融服务是指金融机构运用货币交易手段融通有价物品，向金融活动参与者和顾客提供的共同受益、获得满足的活动。按照世界贸易组织附件的内容，金融服务的提供者包括下列类型机构：保险及其相关服务，还包括所有银行和其他金融服务（保险除外）', NULL);
INSERT INTO `second_class` VALUES (19, '军民融合展示中心', 4, '新城区军民融合创新发展中心由西安市新城区政府批准设立的军民融合创新发展平台，主要服务于政府部门、军工院所、军队装备部门和重点民营企业。\r\n2018年，新城区军民融合创新发展中心获批成为西安市第三批系统推进全面创新改革试点单位', NULL);
INSERT INTO `second_class` VALUES (20, '合作机构\r介绍', 4, '国际合作办学机构异于国际合作项目，主要理解为国际合作办学机构为机构，而国际合作办学项目仅为项目，而非机构。要分清楚项目与机构，我们首先对单一词汇进行分析。', NULL);
INSERT INTO `second_class` VALUES (21, '快响小组', 4, '2018年5月29日至31日，快响小组在深圳组织对首批需求解决方案在深圳进行第二轮评议。根据快响小组定位，按照同等条件下深圳地区商业企业优先原则，本轮对前期遴选的6个技术方向18家企业开展了现场对接交流，对符合条件的单位提出了项目推进意见。', NULL);
INSERT INTO `second_class` VALUES (22, '检验检测\r中心\r', 4, '广饶县检验检测中心以搭建“公共实验室”平台为目标，整合县质监、农业、畜牧、食品药品、海洋渔业等部门的检验检测资源建设而成。根据“部门分头抽检、样品统一检测、信息定期报送”的运行机制，承担着全广饶县各类食品的检验检测任务。广饶县检验检测中心于2014年6月首次通过食品检验检测机构资质认定，取得了76个产品、1058个参数的检验检测能力，成为全国最早完成食品检测资源整合，取得检验资质的机构之一。', NULL);
INSERT INTO `second_class` VALUES (23, '军代表联络室\r', 4, '第一条　为发挥军队各级党代表大会代表作用，坚持和完善党代表大会制度，推进党内民主建设，加强军队各级党组织能力建设和先进性建设，根据《中国共产党章程》、《中国共产党全国代表大会和地方各级代表大会代表任期制暂行条例》、《中国人民解放军政治工作条例》和党内有关规定，制定本实施办法。', NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
