#!bin/sh
sudo add-apt-repository ppa:openjdk-r/ppa
sudo apt-get -y update
sudo apt-get -y install openjdk-8-jdk
sudo apt-get -y install git
sudo apt install mysql-server
sudo mysql_secure_installation
sudo mysql -e "create database my_blog;"
sudo mysql -e "create user 'blog'@'localhost' identified by 'blog';"
sudo mysql -e "grant all on my_blog.* to 'blog'@'localhost';"