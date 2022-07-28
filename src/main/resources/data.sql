INSERT INTO SAMPLE VALUES('1', 'VALUE1');
INSERT INTO SAMPLE VALUES('2', 'VALUE2');

INSERT INTO DEVICE_TYPE(ID, NAME, PLATFORM) VALUES(1, 'Windows Workstation', 'WINDOWS');
INSERT INTO DEVICE_TYPE(ID, NAME, PLATFORM) VALUES(2, 'Windows Server', 'WINDOWS');
INSERT INTO DEVICE_TYPE(ID, NAME, PLATFORM) VALUES(3, 'Mac Workstation', 'MAC');
INSERT INTO DEVICE_TYPE(ID, NAME, PLATFORM) VALUES(4, 'Linux Workstation', 'LINUX');

INSERT INTO DEVICE(ID, SYSTEM_NAME, DEVICE_TYPE_ID, ACTIVE, CREATE_DATE) VALUES (1, 'Windows XP', 1, 'Y', '2021-08-25 15:29:03.991');
INSERT INTO DEVICE(ID, SYSTEM_NAME, DEVICE_TYPE_ID, ACTIVE, CREATE_DATE) VALUES (2, 'Windows 7', 1, 'Y', '2021-08-25 15:29:03.991');
INSERT INTO DEVICE(ID, SYSTEM_NAME, DEVICE_TYPE_ID, ACTIVE, CREATE_DATE) VALUES (3, 'MacBook', 3, 'Y', '2021-08-25 15:29:03.991');
INSERT INTO DEVICE(ID, SYSTEM_NAME, DEVICE_TYPE_ID, ACTIVE, CREATE_DATE) VALUES (4, 'MacMini', 3, 'Y', '2021-08-25 15:29:03.991');
INSERT INTO DEVICE(ID, SYSTEM_NAME, DEVICE_TYPE_ID, ACTIVE, CREATE_DATE) VALUES (5, 'MacPro', 3, 'Y', '2021-08-25 15:29:03.991');

INSERT INTO SERVICE(ID, DESCRIPTION, PLATFORM, COAST) VALUES (1, 'Devices', 'ALL', 4);
INSERT INTO SERVICE(ID, DESCRIPTION, PLATFORM, COAST) VALUES (2, 'Antivirus Win', 'WINDOWS', 5);
INSERT INTO SERVICE(ID, DESCRIPTION, PLATFORM, COAST) VALUES (3, 'Antivirus Mac', 'MAC', 7);
INSERT INTO SERVICE(ID, DESCRIPTION, PLATFORM, COAST) VALUES (4, 'Backup', 'ALL', 3);
INSERT INTO SERVICE(ID, DESCRIPTION, PLATFORM, COAST) VALUES (5, 'PSA', 'ALL', 3);
INSERT INTO SERVICE(ID, DESCRIPTION, PLATFORM, COAST) VALUES (6, 'Screen Share', 'ALL', 1);

INSERT INTO CLIENT(ID, NAME, ACTIVE, CREATE_DATE) VALUES (1, 'Client 1', 'Y', '2021-08-25 15:29:03.991');

INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (1, 1, 1, 1, '2021-08-25 15:29:03.991');
INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (2, 1, 2, 1, '2021-08-25 15:29:03.991');
INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (3, 1, 4, 1, '2021-08-25 15:29:03.991');
INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (4, 1, 6, 1, '2021-08-25 15:29:03.991');

INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (5, 1, 1, 2, '2021-08-25 15:29:03.991');
INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (6, 1, 2, 2, '2021-08-25 15:29:03.991');
INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (7, 1, 4, 2, '2021-08-25 15:29:03.991');
INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (8, 1, 6, 2, '2021-08-25 15:29:03.991');

INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (9, 1, 1, 3, '2021-08-25 15:29:03.991');
INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (10, 1, 3, 3, '2021-08-25 15:29:03.991');
INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (11, 1, 4, 3, '2021-08-25 15:29:03.991');
INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (12, 1, 6, 3, '2021-08-25 15:29:03.991');

INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (13, 1, 1, 4, '2021-08-25 15:29:03.991');
INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (14, 1, 3, 4, '2021-08-25 15:29:03.991');
INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (15, 1, 4, 4, '2021-08-25 15:29:03.991');
INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (16, 1, 6, 4, '2021-08-25 15:29:03.991');

INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (17, 1, 1, 5, '2021-08-25 15:29:03.991');
INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (18, 1, 3, 5, '2021-08-25 15:29:03.991');
INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (19, 1, 4, 5, '2021-08-25 15:29:03.991');
INSERT INTO CLIENT_SERVICE_DEVICE(ID, CLIENT_ID, SERVICE_ID, DEVICE_ID, SERVICE_DATE) VALUES (20, 1, 6, 5, '2021-08-25 15:29:03.991');

/*
-- query to sum by client
SELECT c.id, c.name, sum(coast) FROM
   CLIENT_SERVICE_DEVICE AS csd,
   CLIENT AS c,
   SERVICE AS s
WHERE c.ID = csd.CLIENT_ID
AND s.ID = csd.SERVICE_ID
GROUP BY c.id, c.name
 */