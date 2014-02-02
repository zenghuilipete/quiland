https://github.com/gitlabhq/gitlabhq/blob/5-0-stable/doc/install/installation.md
1 Packages / Dependencies
	sudo apt-get update
	sudo apt-get upgrade
	sudo apt-get install -y build-essential zlib1g-dev libyaml-dev libssl-dev libgdbm-dev libreadline-dev libncurses5-dev libffi-dev curl git-core openssh-server redis-server postfix checkinstall libxml2-dev libxslt-dev libcurl4-openssl-dev libicu-dev

	# Install Python
	sudo apt-get install python
	# Make sure that Python is 2.5+ (3.x is not supported at the moment)
	python --version
	# If it's Python 3 you might need to install Python 2 separately
	sudo apt-get install python2.7
	# Make sure you can access Python via python2
	python2 --version
	# If you get a "command not found" error create a link to the python binary
	sudo ln -s /usr/bin/python /usr/bin/python2

2 Ruby
	Download and compile it:
		mkdir /tmp/ruby && cd /tmp/ruby
		curl --progress http://ftp.ruby-lang.org/pub/ruby/1.9/ruby-1.9.3-p327.tar.gz | tar xz
		cd ruby-1.9.3-p327
		./configure
		make && sudo make install
	Install the Bundler Gem:
		sudo gem install bundler
			Fetching: bundler-1.3.5.gem (100%)
			Successfully installed bundler-1.3.5
			1 gem installed
			Installing ri documentation for bundler-1.3.5...
			Installing RDoc documentation for bundler-1.3.5...

3 System Users
	sudo adduser --disabled-login --gecos 'GitLab' git

4 GitLab shell
	# Login as git
		sudo su git
	# Go to home directory
		cd /home/git
	# Clone gitlab shell
		git clone https://github.com/gitlabhq/gitlab-shell.git
		cd gitlab-shell
	# switch to right version for v5.0
		git checkout v1.1.0
		git checkout -b v1.1.0
		cp config.yml.example config.yml
	# Edit config and replace gitlab_url with something like 'http://domain.com/'
		vim config.yml
	# Do setup
		./bin/install
			mkdir -p /home/git/repositories: true
			mkdir -p /home/git/.ssh: true
			touch /home/git/.ssh/authorized_keys: true
			chmod -R ug+rwX,o-rwx /home/git/repositories: true
			find /home/git/repositories -type d -print0 | xargs -0 chmod g+s: true

5 Database
	# Install the database packages
		sudo apt-get install -y mysql-server mysql-client libmysqlclient-dev
	# Login to MySQL
		mysql -u root -p
	# Create a user for GitLab. (change $password to a real password)
		mysql> CREATE USER 'gitlab'@'localhost' IDENTIFIED BY '$password';
	# Create the GitLab production database
		mysql> CREATE DATABASE IF NOT EXISTS `gitlabhq_production` DEFAULT CHARACTER SET `utf8` COLLATE `utf8_unicode_ci`;
	# Grant the GitLab user necessary permissopns on the table.
		mysql> GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER ON `gitlabhq_production`.* TO 'gitlab'@'localhost';
	# Quit the database session
		mysql> \q
	# Try connecting to the new database with the new user
		sudo -u git -H mysql -u gitlab -p -D gitlabhq_production

6 GitLab
	1 Clone the Source
		cd /home/git
		# Clone GitLab repository
			sudo -u git -H git clone https://github.com/gitlabhq/gitlabhq.git gitlab
		# Go to gitlab dir
			cd /home/git/gitlab
		# Checkout to stable release
			sudo -u git -H git checkout 5-0-stable
(
https://codeload.github.com/gitlabhq/gitlabhq/zip/5-0-stable
https://codeload.github.com/gitlabhq/gitlabhq/zip/master
C:\Users\Administrator\Desktop>scp gitlabhq-5-0-stable.zip eric@192.168.0.141:/home/eric
sudo mv /home/eric/gitlabhq-5-0-stable.zip /home/git/
sudo unzip gitlabhq-5-0-stable.zip
sudo unzip gitlabhq-5-0-stable.zip
sudo chown -R git:git gitlabhq-5-0-stable/
)
	2 Configure it
		cd /home/git/gitlab
		# Copy the example GitLab config
			sudo -u git -H cp config/gitlab.yml.example config/gitlab.yml
		# Make sure to change "localhost" to the fully-qualified domain name of your
		# host serving GitLab where necessary
			sudo -u git -H nano config/gitlab.yml
		# Make sure GitLab can write to the log/ and tmp/ directories
			sudo chown -R git log/
			sudo chown -R git tmp/
			sudo chmod -R u+rwX  log/ && sudo chmod -R u+rwX  tmp/
		# Create directory for satellites
			sudo -u git -H mkdir /home/git/gitlab-satellites
		# Create directory for pids and make sure GitLab can write to it
			sudo -u git -H mkdir tmp/pids/
			sudo chmod -R u+rwX  tmp/pids/
		# Copy the example Unicorn config
			sudo -u git -H cp config/unicorn.rb.example config/unicorn.rb
		Important Note: Make sure to edit both files to match your setup.

	3 Configure GitLab DB settings
		# Mysql
		sudo -u git cp config/database.yml.mysql config/database.yml
		# PostgreSQL
		sudo -u git cp config/database.yml.postgresql config/database.yml

	4 Install Gems
		cd /home/git/gitlab
		sudo gem install charlock_holmes --version '0.6.9'
		# For MySQL (note, the option says "without")
		sudo -u git -H bundle install --deployment --without development test postgres
		# Or for PostgreSQL
		sudo -u git -H bundle install --deployment --without development test mysql

	5 Initialise Database and Activate Advanced Features
		sudo -u git -H bundle exec rake gitlab:setup RAILS_ENV=production

	6 Install Init Script
		sudo curl --output /etc/init.d/gitlab https://raw.github.com/gitlabhq/gitlab-recipes/5-0-stable/init.d/gitlab
		sudo chmod +x /etc/init.d/gitlab
		#Make GitLab start on boot:
		sudo update-rc.d gitlab defaults 21

	7 Check Application Status
		#Check if GitLab and its environment are configured correctly:
		sudo -u git -H bundle exec rake gitlab:env:info RAILS_ENV=production
		#To make sure you didn't miss anything run a more thorough check with:
		sudo -u git -H bundle exec rake gitlab:check RAILS_ENV=production

	8 Start Your GitLab Instance
		sudo service gitlab start
		# or
		sudo /etc/init.d/gitlab restart

7 Nginx
	sudo apt-get install nginx
	Site Configuration
		sudo curl --output /etc/nginx/sites-available/gitlab https://raw.github.com/gitlabhq/gitlab-recipes/5-0-stable/nginx/gitlab
		sudo ln -s /etc/nginx/sites-available/gitlab /etc/nginx/sites-enabled/gitlab
		# Change **YOUR_SERVER_IP** and **YOUR_SERVER_FQDN**
		# to the IP address and fully-qualified domain name
		# of your host serving GitLab
		sudo nano /etc/nginx/sites-available/gitlab
		sudo service nginx restart