DELETE from TTasks where id=id
DELETE from TCategories where id=id
DELETE from TUsers where id=id


Insert into TUsers VALUES(1,'usuario1@gmail.com',FALSE,'usuario1','usuario1','ENABLED');
Insert into TUsers VALUES(2,'usuario2@gmail.com',FALSE,'usuario2','usuario2','ENABLED');
Insert into TUsers VALUES(3,'administrador1@gmail.com',TRUE,'administrador1','administrador1','ENABLED');

Insert into TTasks VALUES(1,'firstTaskCreated','2017-01-01', null, '2017-01-01','First Task',null,1);
Insert into TTasks VALUES(2,'secondTaskCreated','2017-01-01', null, '2017-06-30','Second Task',null,1);

Insert into TCategories VALUES(1,'FirstCategory',1);

Insert into TTasks VALUES(3,'thirdTaskCreated','2017-02-01', null, '2017-02-05','Third Task',1,1);


Insert into TTasks VALUES(4,'fourthTaskCreated','2017-01-01', null, '2017-01-01','Fourth Task',null,2);
Insert into TTasks VALUES(5,'fifthTaskCreated','2017-01-01', null, '2017-06-30','Fifth Task',null,2);

Insert into TCategories VALUES(2,'SecondCategory',2);

Insert into TTasks VALUES(6,'sixthTaskCreated','2017-02-01', null, '2017-02-05','Sixth Task',2,2);

Insert into TTasks VALUES(7,'seventhTaskCreated','2017-01-01', null, '2017-01-01','Seventh Task',null,3);
Insert into TTasks VALUES(8,'eighthTaskCreated','2017-01-01', null, '2017-06-30','Eighth Task',null,3);

Insert into TCategories VALUES(3,'ThirdCategory',3);

Insert into TTasks VALUES(9,'ninthTaskCreated','2017-02-01', null, '2017-02-05','Ninth Task',3,3);
