eval "$(pyenv init -)"
eval "$(pyenv init -)"
export PIPENV_VENV_IN_PROJECT=1
# postgres export
export PATH=$PATH:/Applications/Postgres.app/Contents/Versions/14/bin

# >>> conda initialize >>>
# !! Contents within this block are managed by 'conda init' !!
__conda_setup="$('/Users/jw/anaconda3/bin/conda' 'shell.zsh' 'hook' 2> /dev/null)"
if [ $? -eq 0 ]; then
    eval "$__conda_setup"
else
    if [ -f "/Users/jw/anaconda3/etc/profile.d/conda.sh" ]; then
        . "/Users/jw/anaconda3/etc/profile.d/conda.sh"
    else
        export PATH="/Users/jw/anaconda3/bin:$PATH"
    fi
fi
unset __conda_setup
# <<< conda initialize <<<

# For dotnet ML package use
export PATH="$PATH:~/.dotnet/tools/mlnet"

# The next line updates PATH for the Google Cloud SDK.
if [ -f '/Users/jw/google-cloud-sdk/path.zsh.inc' ]; then . '/Users/jw/google-cloud-sdk/path.zsh.inc'; fi

# The next line enables shell command completion for gcloud.
if [ -f '/Users/jw/google-cloud-sdk/completion.zsh.inc' ]; then . '/Users/jw/google-cloud-sdk/completion.zsh.inc'; fi


export PATH="$HOME/.poetry/bin:$PATH"

# opam configuration
[[ ! -r /Users/jw/.opam/opam-init/init.zsh ]] || source /Users/jw/.opam/opam-init/init.zsh  > /dev/null 2> /dev/null
export PATH="/usr/local/opt/libpq/bin:$PATH"

# For ACE Bayesian Network Solver
export PATH=$PATH:/Users/jw/Desktop/ACE
export PATH="/usr/local/opt/curl/bin:$PATH"

#THIS MUST BE AT THE END OF THE FILE FOR SDKMAN TO WORK!!!
export SDKMAN_DIR="$HOME/.sdkman"
[[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]] && source "$HOME/.sdkman/bin/sdkman-init.sh"

# For REAL (introduction to database)
export PATH=$PATH"/Users/jw/Desktop/idb_REAL/real-0.6.1.jar"

# For JAVA Maven project build
export PATH=$PATH:/Users/jw/apache-maven-3.9.0/bin

# For TCP-DS generator
export PATH=$PATH:/Users/jw/Desktop/tpcds-kit/tools/
export PATH="/usr/local/opt/curl/bin:$PATH"
export PATH="/usr/local/opt/libpq/bin:$PATH"
export PATH=${PATH}:/usr/local/mysql/bin/
# For setting scala sbt heap size (resolve out-of-memory issue)
export SBT_OPTS="-Xmx10G -Xss2M"

# verdict DB runs with JDK1.8 only and JDK1.7 does not support presto for benchmarking
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
