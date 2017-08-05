# play-ping-pong ![Travis CI](https://travis-ci.org/rydogs/play-ping-pong.svg?branch=master) [![Dependency Status](https://www.versioneye.com/user/projects/5985400b6725bd0076c18b82/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5985400b6725bd0076c18b82)
A Slack intregration for ping pong using spring-boot

## Development
After cloning, run `mvn spring-boot:run` to start the application.  It should be available at http://localhost:8080

## Deployment
* To deploy to heroku, first make sure to add heroku as a git remote with:

`git remote add heroku https://git.heroku.com/playpingpong.git`

* Use the following command to push to heroku master:

`git push heroku master`

* If for some reason you would like to push a branch to herkou, use the following instead:

`git push heroku <branchname>:master`
