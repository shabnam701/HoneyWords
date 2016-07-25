# HoneyWords

An adversary who has stolen a file of hashed passwords can often use brute-force search to find a password p whose hash value H(p) equals the hash value stored for a given user’s password, thus allowing the adversary to impersonate the user.
We propose a simple method for improving the security of hashed passwords: the maintenance of additional “honeywords” (false passwords) associated with each user’s account.

An adversary who steals a file of hashed passwords and inverts the hash function cannot tell if he has found the password or a honeyword. The attempted use of a honeyword for login sets off an alarm.

Modules:
1.User 
2.Admin
3.Attacker
4.Honeychecker:An auxilary server

1.User
a) User Login:  User has to login by using his user name and password. If he is not a registered user he has to register and login.
b)File Uploading/Downloading: User Can upload/download the file.

2.Admin : He can View the Status History and user details. He keeps track of the user and is notified when an attack takes place.

3.Attacker: Attacker will find the hashed password by using Brute force Method and tries to login.

4.Honeycheck: An auxiliary server (the “honeychecker”) can distinguish the user password from honeywords for the login routine, and will set off an alarm if a honeyword is submitted.
