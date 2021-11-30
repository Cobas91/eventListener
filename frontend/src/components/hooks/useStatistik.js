import {
  API_getStatistikInformation,
  API_getStatistikInformationByAction,
  API_getBarChartData,
} from '../../service/statistikService'

import { useEffect, useState } from 'react'

export default function useStatistik() {
  const [statistic, setStatistic] = useState()
  const [statisticByAction, setStatisticByAction] = useState()
  const [barChartData, setBarChartData] = useState([])

  useEffect(() => {
    getAllStatistics()
    getBarChartData()
  }, [])

  const getAllStatistics = () => {
    API_getStatistikInformation().then(statistics => {
      setStatistic(statistics)
    })
  }

  const getStatisticsByAction = action => {
    return API_getStatistikInformationByAction(action).then(statistics => {
      setStatisticByAction(statistics)
    })
  }

  const getBarChartData = () => {
    API_getBarChartData().then(res => {
      setBarChartData(res)
    })
  }

  return { statistic, statisticByAction, getStatisticsByAction, barChartData }
}
