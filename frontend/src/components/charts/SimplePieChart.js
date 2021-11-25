import * as React from 'react'
import { PieChart, Pie, Legend, Tooltip, ResponsiveContainer } from 'recharts'

export default function SimplePieChart({ data }) {
  return (
    <ResponsiveContainer width="100%" height="100%">
      <PieChart width="100%" height="100%">
        <Pie
          dataKey="amount"
          isAnimationActive={false}
          data={data}
          cx="50%"
          cy="50%"
          outerRadius={100}
          fill="#8884d8"
          label
        />
        <Tooltip />
        <Legend />
      </PieChart>
    </ResponsiveContainer>
  )
}
