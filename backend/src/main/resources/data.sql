INSERT INTO USERS (username, password)
SELECT 'iendjei', '$2a$10$WT2PM4WDTB9XFPo.iyFbHeJ7dPbALpLttzqbfDukaPy9zV96huEbC'
WHERE NOT EXISTS (SELECT 1 FROM USERS WHERE username = 'iendjei');