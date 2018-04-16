app.factory('RankService', ['rankStat', function(rankStat) {

    var calcRankMean = function(rank) {
        if(!rank) rank = {};
        var deadProb = rank.deadProb * rankStat.deadWeight;
        var exitedProb = rank.exitedProb * rankStat.exitedWeight;
        var operatingProb = rank.operatingProb * rankStat.operatingWeight;
        return parseInt((deadProb + exitedProb + operatingProb) / rankStat.noOfEvents * 100);
    };

    return {
        calcRankMean: calcRankMean
    };
}]);