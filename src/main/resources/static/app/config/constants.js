/**
 * Created by Harrison on 3/17/2018.
 */

app.constant('tagUrl', '/api/tag')
    .constant('ideaUrl', '/api/idea')
    .constant('feedUrl', '/api/feed')
    .constant('userUrl', '/api/user')
    .constant('walletUrl', '/api/wallet')
    .constant('baseUrl', 'localhost:9000')
    .constant('authInfo', {
        clientId: 'starthubclientidforextremeusers',
        clientSecret: 'XY7kmzoNzl100',
        grantType: 'password'
    })
    .constant('rankStat', {
        deadWeight: 0,
        exitedWeight: 5,
        operatingWeight: 4,
        noOfEvents: 3
    });