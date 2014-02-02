http://unixhelp.ed.ac.uk/CGI/man-cgi?tar
NOTE: click here if you get an empty page.

TAR(1)              tar       TAR(1)

NAME
tar - The GNU version of the tar archiving utility

SYNOPSIS
       tar <operation> [options]

       Operations:
       [-]A --catenate --concatenate
       [-]c --create
       [-]d --diff --compare
       [-]r --append
       [-]t --list
       [-]u --update
       [-]x --extract --get
       --delete

       Common Options:
       -C, --directory DIR
       -f, --file F
       -j, --bzip2
       -p, --preserve-permissions
       -v, --verbose
       -z, --gzip



EXAMPLES

       tar -xvf foo.tar
        verbosely extract foo.tar

       tar -xzf foo.tar.gz
        extract gzipped foo.tar.gz

       tar -cjf foo.tar.bz2 bar/
        create   bzipped   tar  archive  of  the  directory  bar  called
        foo.tar.bz2

       tar -xjf foo.tar.bz2 -C bar/
        extract bzipped foo.tar.bz2 after changing directory to bar

       tar -xzf foo.tar.gz blah.txt
        extract the file blah.txt from foo.tar.gz

FUNCTION LETTERS

       One of the following options must be used:

       -A, --catenate, --concatenate
        append tar files to an archive

       -c, --create
        create a new archive

       -d, --diff, --compare
        find differences between archive and file system

       -r, --append
        append files to the end of an archive

       -t, --list
        list the contents of an archive

       -u, --update
        only append files that are newer than the existing in archive

       -x, --extract, --get
        extract files from an archive

       --delete
        delete from the archive (not for use on mag tapes!)

COMMON OPTIONS

       -C, --directory DIR
        change to directory DIR

       -f, --file [HOSTNAME:]F
        use archive file or device F (default "-", meaning stdin/stdout)

       -j, --bzip2
        filter archive through bzip2, use to decompress .bz2 files

       -p, --preserve-permissions
        extract all protection information

       -v, --verbose
        verbosely list files processed

       -z, --gzip, --ungzip
        filter the archive through gzip

ALL OPTIONS

       --atime-preserve
        don't change access times on dumped files

       -b, --blocking-factor N
        block size of Nx512 bytes (default N=20)

       -B, --read-full-blocks
        reblock as we read (for reading 4.2BSD pipes)

       --backup BACKUP-TYPE
        backup  files  instead of deleting them using BACKUP-TYPE simple
        or numbered

       --block-compress
        block the output of compression program for tapes

       -C, --directory DIR
        change to directory DIR

       --check-links
        warn if number of hard links to the file on the filesystem  mis-
        match the number of links recorded in the archive

       --checkpoint
        print directory names while reading the archive

       -f, --file [HOSTNAME:]F
        use archive file or device F (default "-", meaning stdin/stdout)

       -F, --info-script F --new-volume-script F
        run script at end of each tape (implies --multi-volume)

       --force-local
        archive file is local even if has a colon

       --format FORMAT
        selects output archive format
        v7 - Unix V7
        oldgnu - GNU tar <=1.12
        gnu - GNU tar 1.13
        ustar - POSIX.1-1988
        posix - POSIX.1-2001

       -g, --listed-incremental F
        create/list/extract new GNU-format incremental backup

       -G, --incremental
        create/list/extract old GNU-format incremental backup

       -h, --dereference
        don't dump symlinks; dump the files they point to

       --help like this manpage, but not as cool

       -i, --ignore-zeros
        ignore blocks of zeros in archive (normally mean EOF)

       --ignore-case
        ignore case when excluding files

       --ignore-failed-read
        don't exit with non-zero status on unreadable files

       --index-file FILE
        send verbose output to FILE instead of stdout

       -j, --bzip2
        filter archive through bzip2, use to decompress .bz2 files

       -k, --keep-old-files
        keep existing files; don't overwrite them from archive

       -K, --starting-file F
        begin at file F in the archive

       --keep-newer-files
        do not overwrite files which are newer than the archive

       -l, --one-file-system
        stay in local file system when creating an archive

       -L, --tape-length N
        change tapes after writing N*1024 bytes

       -m, --touch, --modification-time
        don't extract file modified time

       -M, --multi-volume
        create/list/extract multi-volume archive

       --mode PERMISSIONS
        apply PERMISSIONS while adding files (see chmod(1))

       -N, --after-date DATE, --newer DATE
        only store files newer than DATE

       --newer-mtime DATE
        like --newer, but with a DATE

       --no-anchored
        match any subsequenceof the name's components with --exclude

       --no-ignore-case
        use case-sensitive matching with --exclude

       --no-recursion
        don't recurse into directories

       --no-same-permissions
        apply user's umask when extracting  files  instead  of  recorded
        permissions

       --no-wildcards
        don't use wildcards with --exclude

       --no-wildcards-match-slash
        wildcards do not match slashes (/) with --exclude

       --null --files-from reads null-terminated names, disable --directory

       --numeric-owner
        always use numbers for user/group names

       -o, --old-archive, --portability
        like  --format=v7;  -o  exhibits  this behavior when creating an
        archive (deprecated behavior)

       -o, --no-same-owner
        do not attempt to restore ownership when extracting; -o exhibits
        this behavior when extracting an archive

       -O, --to-stdout
        extract files to standard output

       --occurrence[=NUMBER]
        process  only  the  NUMBERth  occurrence  of  each  file  in the
        archive; this option is valid only in conjunction  with  one  of
        the subcommands --delete, --diff, --extract or --list and when a
        list of files is given either on the command line or via the  -T
        option; NUMBER defaults to 1

       --no-overwrite-dir
        preserve metadata of existing directories

       --overwrite
        overwrite existing files and directory metadata when extracting

       --overwrite-dir
        overwrite directory metadata when extracting

       --owner USER
        change owner of extraced files to USER

       -p, --same-permissions, --preserve-permissions
        extract all protection information

       -P, --absolute-names
        don't strip leading '/'s from file names

       --pax-option KEYWORD-LIST
        used  only with POSIX.1-2001 archives to modify the way tar han-
        dles extended header keywords

       --posix
        like --format=posix

       --preserve
        like --preserve-permissions --same-order

       --acls this option causes tar to store each file's ACLs in the archive.

       --selinux
        this  option  causes  tar  to store each file's SELinux security
        context information in the archive.

       --xattrs
        this option causes tar to store each file's extended  attributes
        in  the archive. This option also enables --acls and--selinux if
        they haven't been set already, due to the fact that the data for
        those are stored in special xattrs.

       --no-acls
        This  option  causes  tar  not  to store each file's ACLs in the
        archive and not to extract any ACL information in an archive.

       --no-selinux
        this option causes tar not to store each file's SELinux security
        context  information  in  the  archive  and  not  to extract any
        SELinux information in an archive.

       --no-xattrs
        this option  causes  tar  not  to  store  each  file's  extended
        attributes  in  the  archive  and  not  to  extract any extended
        attributes in an archive. This option also enables --no-acls and
        --no-selinux if they haven't been set already.

       -R, --record-number
        show record number within archive with each message

       --record-size SIZE
        use SIZE bytes per record when accessing archives

       --recursion
        recurse into directories

       --recursive-unlink
        remove existing directories before extracting directories of the
        same name

       --remove-files
        remove files after adding them to the archive

       --rmt-command CMD
        use CMD instead of the default /usr/sbin/rmt

       --rsh-command CMD
        use remote CMD instead of rsh(1)

       -s, --same-order, --preserve-order
        list of names to extract is sorted to match archive

       -S, --sparse
        handle sparse files efficiently

       --same-owner
        create extracted files with the same ownership

       --show-defaults
        display the default options used by tar

       --show-omitted-dirs
        print directories tar skips while operating on an archive

       --strip-components NUMBER, --strip-path NUMBER
        strip NUMBER  of  leading  components  from  file  names  before
        extraction

        (1) tar-1.14 uses --strip-path, tar-1.14.90+ uses --strip-compo-
        nents

       --suffix SUFFIX
        use SUFFIX instead of default '~' when backing up files

       -T, --files-from F
        get names to extract or create from file F

       --totals
        print total bytes written with --create

       -U, --unlink-first
        remove existing files before extracting files of the same name

       --use-compress-program PROG
        access the archive through PROG which is generally a compression
        program

       --utc  display file modification dates in UTC

       -v, --verbose
        verbosely list files processed

       -V, --label NAME
        create archive with volume name NAME

       --version
        print tar program version number

       --volno-file F
        keep track of which volume of a multi-volume archive its working
        in FILE; used with --multi-volume

       -w, --interactive, --confirmation
        ask for confirmation for every action

       -W, --verify
        attempt to verify the archive after writing it

       --wildcards
        use wildcards with --exclude

       --wildcards-match-slash
        wildcards match slashes (/) with --exclude

       --exclude PATTERN
        exclude files based upon PATTERN

       -X, --exclude-from FILE
        exclude files listed in FILE

       -Z, --compress, --uncompress
        filter the archive through compress

       -z, --gzip, --gunzip, --ungzip
        filter the archive through gzip

       --use-compress-program PROG
        filter the archive through PROG (which must accept -d)

       -[0-7][lmh]
        specify drive and density

BUGS


       The GNU folks, in general, abhor man pages, and create  info  documents
       instead.   The  maintainer  of tar falls into this category.  Thus this
       man page may not be complete, nor current, and was included in the  Red
       Hat  CVS  tree because man is a great tool :).  This man page was first
       taken from Debian Linux and has since been loving updated here.

REPORTING BUGS


       Please report bugs via https://bugzilla.redhat.com

SEE ALSO


       The full documentation for tar is maintained as a Texinfo  manual.   If
       the info and tar programs are properly installed at your site, the com-
       mand

        info tar

       should give you access to the complete manual.

AUTHORS


       Debian Linux http://www.debian.org/
       Mike Frysinger <vapier@gentoo.org>

GNU          Oct 2004       TAR(1)
� 1994 Man-cgi 1.15, Panagiotis Christias <christia@theseas.ntua.gr>