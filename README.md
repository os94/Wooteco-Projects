# Branch 관리

- `git remote add NEWREMOTE https://github.com/os94/REPO`
- `git remote -v` 확인
- **master 브랜치에서** 새로운 브랜치 생성하며 checkout
	- `git checkout -b MYNEWBRANCH`
- master 브랜치에서 가져와진 커밋을 1개만 남기고 리셋
	- `git reset 5ae1183` & discard change
- `git fetch NEWREMOTE REMOTEBRANCH`
- `git rebase NEWREMOTE/REMOTEBRANCH`
	- conflict 나지만, README만 다르므로 쉽게 해결가능
	- project의 README로 맞춰준다
	- 이때 conflict 해결에 따라 'first commit'이 따라붙기도 안붙기도한다
- Publish Branch
- `git checkout master`
- (Option) `git remote remove NEWREMOTE`
- master 브랜치의 README.md 업데이트