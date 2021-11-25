import * as React from 'react'
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from 'recharts'
export default function SimpleBarChart({ barChartData }) {
  return (
    <ResponsiveContainer width="100%" height="100%">
      <BarChart
        width="100%"
        height="100%"
        data={barChartData}
        margin={{
          top: 5,
          right: 30,
          left: 20,
          bottom: 5,
        }}
      >
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="name" />
        <YAxis />
        <Tooltip />
        <Legend />
        <Bar dataKey="amount" fill="var(--primary-blue)" />
      </BarChart>
    </ResponsiveContainer>
  )
}
