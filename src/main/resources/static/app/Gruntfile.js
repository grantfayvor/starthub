module.exports = function (grunt) {

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        concat: {
            options: {
                separator: ';'
            },
            dist: {
                src: [
                    'lib/stomp.js',
                    'angular/angular.js',
//                    'angular/angular-sanitize.js',
                    'angular/angular-cookies.js',
                    'angular/angular-ui-router.js',
                    'angular/angular-summernote.js',
                    'config/*.js',
                    'service/*.js',
                    'main.js',
                    'modules/**/*.js'],
                dest: 'compiled/js/<%= pkg.name %>.js'
            }
        },
        uglify: {
            options: {
                banner: '/*! <%= pkg.name %> <%= grunt.template.today() %> */\n'
            },
            dist: {
                files: {
                    'compiled/js/<%= pkg.name %>.min.js': ['<%= concat.dist.dest %>']
                }
            }
        },
        jshint: {
            files: ['Gruntfile.js',
                'config/*.js',
                'service/*.js',
                'main.js',
                'modules/**/*.js'],
            options: {
                globals: {
                    jQuery: true,
                },
                'esversion': 6
            }
        },
        watch: {
            files: ['<%= jshint.files %>'],
            tasks: ['jshint', 'concat', 'uglify']
        }

    });

    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-concat');


    grunt.registerTask('default', ['jshint', 'concat', 'uglify', 'watch']);

};