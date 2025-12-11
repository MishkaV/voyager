#!/bin/sh

echo "Running light code quality checks before pushing"

# Check for uncommitted changes
if ! git diff --quiet || ! git diff --cached --quiet; then
  echo "⚠️ Warning: You have uncommitted or unstaged changes."
  echo "🔴 It's recommended to commit or stash them before pushing."
fi

########################################

# Detect parent branch (main or develop)
PARENT_BRANCH=$(git rev-parse --abbrev-ref origin/HEAD | sed 's@origin/@@')

# Find the latest commit on the parent branch
LATEST_PARENT_COMMIT=$(git rev-parse origin/$PARENT_BRANCH)

# Find the latest commit on the current branch
LATEST_CURRENT_COMMIT=$(git merge-base HEAD origin/$PARENT_BRANCH)

# Check if the branch is rebased on the latest commit from the parent branch
if [ "$LATEST_PARENT_COMMIT" != "$LATEST_CURRENT_COMMIT" ]; then
  echo "⚠️ Warning: Your branch is not rebased on the latest '$PARENT_BRANCH'."
  echo "🔄 Please rebase before pushing to avoid conflicts."
fi

########################################

# Check for secrets using GitLeaks (install GitLeaks if missing)
if command -v gitleaks &> /dev/null; then
  echo "🔒 Checking for secrets..."
  gitleaks detect --redact --source . --exit-code 1
else
  echo "⚠️ Warning: GitLeaks not found. Install it for secret scanning."
fi

########################################

# Run Detekt check
./gradlew :detekt --continue --warn
DetektStatus=$?

# Verify if all checks passed
if [ $DetektStatus -ne 0 ]; then
  echo "❌ Detekt check has failed. Please fix issues before pushing."
  exit 1
fi


########################################

echo "✅ All checks passed successfully. Proceeding with push..."
exit 0