Migration Process
======

## SVN to GIT Repo conversion
To convert a svn repo to an equivalent got repo. A number of steps have to be followed. This includes -
1. Retaining the original authors
2. Retaining file history
3. Restructuring if required (with file history intact).

### Preparing authors file:
This is basically a mapping file that is used to map a svn user with its equivalent git user. A sample record is of the format:

`svn_user_name = git_user_name <email_id>`

Sample record:

`himbhar = himabhar <him@abc.com>`

Atlassian already provided a utility for generating the authors file, added as a folder here. Also refer [Atlassian link][https://www.atlassian.com/git/tutorials/migrating-overview]. Utility can be downloaded from [here][https://bitbucket.org/atlassian/svn-migration-scripts/downloads?_ga=2.242793666.1173704789.1509797567-376563498.1505280010]

Verify the utility jar - `java -jar ~/svn-migration-scripts.jar verify`

*How to generate the authors file?*
```bash
cd ~/GitMigration
java -jar ~/svn-migration-scripts.jar authors <svn-repo> > authors.txt
```

With authors file now ready, we can move to next steps.

### SVN Clone
svn is converted to a git repo via git svn clone command. e.g.

```bash
git svn clone --trunk=<trunk-uri> --authors=<path to authors.txt file> <url to svn repo root> <name of git repo>
```

### Preserve file history (For Consolidation)
If we need to merge the above repo in an existing git repo, then we need to do some git log rewriting. This is done to preserve the individual file history. We do not do log rewrite on the original cloned repo, instead we clone a copy from it.

```bash
mkdir cloned_git_repo_name
git clone -c core.longpaths=true file:///c/migrationCode/git_repo_name
```

Do log rewrite since we moved all files to sub-directory called `abc`.

```bash
git filter-branch --tree-filter "mkdir -p /tmp/abc;mv * /tmp/abc; mv /tmp/abc ./" HEAD
```

### Merging (For Consolidation)
Now all we are left is to merge into a git repo where we want this repo to be a subfolder. Let's say we want git repo `r1` to be migrated inside `test-repo`

```bash
cd test-repo
git checkout master
git remote add -f svn_r1 file:///c/migrationCode/cloned_git_repo_name 
git merge svn_r1 --allow-unrelated-histories
```

### Repository Conversion (For extraction)
This process outlined here is required if we want to split a sub-project into a git repository. For our case we needed to build `xyz` under `abc` as an independent repo.

  1. Step 1, clone the complete abc repository.
     `git clone -c core.longpaths=true file:///c/migrationCode/abc` 
  2. Step 2, now we convert `xyz` module into repository.
     `git filter-branch --prune-empty --subdirectory-filter xyz`
  3. Given that we has added `xyz` as a new repository, we must remove it from the original `abc` repository. The folloing command removed the `xyz` folder and all attached history. Run this command on `abc` repo
     `git filter-branch --tree-filter 'rm -rf xyz' --prune-empty HEAD`
  4. Push changes to upstream. `git push`